package com.netflix.conductor.grpc.server.service;

import com.google.protobuf.Empty;
import com.google.rpc.DebugInfo;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.protobuf.lite.ProtoLiteUtils;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public class GRPCHelper {
    private final Logger logger;

    private static final Metadata.Key<DebugInfo> STATUS_DETAILS_KEY =
            Metadata.Key.of(
                    "grpc-status-details-bin",
                    ProtoLiteUtils.metadataMarshaller(DebugInfo.getDefaultInstance()));

    public GRPCHelper(Logger log) {
        this.logger = log;
    }

    /**
     * Converts an internal exception thrown by Conductor into an StatusException
     * that uses modern "Status" metadata for GRPC.
     *
     * Note that this is trickier than it ought to be because the GRPC APIs have
     * not been upgraded yet. Here's a quick breakdown of how this works in practice:
     *
     * Reporting a "status" result back to a client with GRPC is pretty straightforward.
     * GRPC implementations simply serialize the status into several HTTP/2 trailer headers that
     * are sent back to the client before shutting down the HTTP/2 stream.
     *
     * - 'grpc-status', which is a string representation of a {@link com.google.rpc.Code}
     * - 'grpc-message', which is the description of the returned status
     * - 'grpc-status-details-bin' (optional), which is an arbitrary payload with a serialized
     *  ProtoBuf object, containing an accurate description of the error in case the status is not
     *  successful.
     *
     *  By convention, Google provides a default set of ProtoBuf messages for the most common
     *  error cases. Here, we'll be using {@link DebugInfo}, as we're reporting an internal
     *  Java exception which we couldn't properly handle.
     *
     *  Now, how do we go about sending all those headers _and_ the {@link DebugInfo} payload
     *  using the Java GRPC API?
     *
     *  The only way we can return an error with the Java API is by passing an instance of
     *  {@link io.grpc.StatusException} or {@link io.grpc.StatusRuntimeException} to
     *  {@link StreamObserver#onError(Throwable)}. The easiest way to create either of these
     *  exceptions is by using the {@link Status} class and one of its predefined code
     *  identifiers (in this case, {@link Status#INTERNAL} because we're reporting an internal
     *  exception). The {@link Status} class has setters to set its most relevant attributes,
     *  namely those that will be automatically serialized into the 'grpc-status' and 'grpc-message'
     *  trailers in the response. There is, however, no setter to pass an arbitrary ProtoBuf message
     *  to be serialized into a `grpc-status-details-bin` trailer. This feature exists in the other
     *  language implementations but it hasn't been brought to Java yet.
     *
     *  Fortunately, {@link Status#asException(Metadata)} exists, allowing us to pass any amount
     *  of arbitrary trailers before we close the response. So we're using this API to manually
     *  craft the 'grpc-status-detail-bin' trailer, in the same way that the GRPC server implementations
     *  for Go and C++ craft and serialize the header. This will allow us to access the metadata
     *  cleanly from Go and C++ clients by using the 'details' method which _has_ been implemented
     *  in those two clients.
     *
     * @param t The exception to convert
     * @return an instance of {@link StatusException} which will properly serialize all its
     * headers into the response.
     */
    private StatusException throwableToStatusException(Throwable t) {
        String[] frames = ExceptionUtils.getStackFrames(t);
        Metadata metadata = new Metadata();
        metadata.put(STATUS_DETAILS_KEY,
                DebugInfo.newBuilder()
                        .addAllStackEntries(Arrays.asList(frames))
                        .setDetail(ExceptionUtils.getMessage(t))
                        .build()
        );

        return Status.INTERNAL
                .withDescription(t.getMessage())
                .withCause(t)
                .asException(metadata);
    }

    void onError(StreamObserver<?> response, Throwable t) {
        logger.error("internal exception during GRPC request", t);
        response.onError(throwableToStatusException(t));
    }

    void emptyResponse(StreamObserver<Empty> response) {
        response.onNext(Empty.getDefaultInstance());
        response.onCompleted();
    }

    String optional(@Nonnull String str) {
        return str.isEmpty() ? null : str;
    }

    String optionalOr(@Nonnull String str, String defaults) {
        return str.isEmpty() ? defaults : str;
    }

    Integer optional(@Nonnull Integer i) {
        return i == 0 ? null : i;
    }

    Integer optionalOr(@Nonnull Integer i, int defaults) {
        return i == 0 ? defaults : i;
    }
}
