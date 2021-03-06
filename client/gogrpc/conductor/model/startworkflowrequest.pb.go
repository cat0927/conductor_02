// Code generated by protoc-gen-go. DO NOT EDIT.
// source: model/startworkflowrequest.proto

package model // import "github.com/netflix/conductor/client/gogrpc/conductor/model"

import proto "github.com/golang/protobuf/proto"
import fmt "fmt"
import math "math"
import _struct "github.com/golang/protobuf/ptypes/struct"

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// This is a compile-time assertion to ensure that this generated file
// is compatible with the proto package it is being compiled against.
// A compilation error at this line likely means your copy of the
// proto package needs to be updated.
const _ = proto.ProtoPackageIsVersion2 // please upgrade the proto package

type StartWorkflowRequest struct {
	Name                 string                    `protobuf:"bytes,1,opt,name=name,proto3" json:"name,omitempty"`
	Version              int32                     `protobuf:"varint,2,opt,name=version,proto3" json:"version,omitempty"`
	CorrelationId        string                    `protobuf:"bytes,3,opt,name=correlation_id,json=correlationId,proto3" json:"correlation_id,omitempty"`
	Input                map[string]*_struct.Value `protobuf:"bytes,4,rep,name=input,proto3" json:"input,omitempty" protobuf_key:"bytes,1,opt,name=key,proto3" protobuf_val:"bytes,2,opt,name=value,proto3"`
	TaskToDomain         map[string]string         `protobuf:"bytes,5,rep,name=task_to_domain,json=taskToDomain,proto3" json:"task_to_domain,omitempty" protobuf_key:"bytes,1,opt,name=key,proto3" protobuf_val:"bytes,2,opt,name=value,proto3"`
	WorkflowDef          *WorkflowDef              `protobuf:"bytes,6,opt,name=workflow_def,json=workflowDef,proto3" json:"workflow_def,omitempty"`
	XXX_NoUnkeyedLiteral struct{}                  `json:"-"`
	XXX_unrecognized     []byte                    `json:"-"`
	XXX_sizecache        int32                     `json:"-"`
}

func (m *StartWorkflowRequest) Reset()         { *m = StartWorkflowRequest{} }
func (m *StartWorkflowRequest) String() string { return proto.CompactTextString(m) }
func (*StartWorkflowRequest) ProtoMessage()    {}
func (*StartWorkflowRequest) Descriptor() ([]byte, []int) {
	return fileDescriptor_startworkflowrequest_57b778443ff5f3ba, []int{0}
}
func (m *StartWorkflowRequest) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_StartWorkflowRequest.Unmarshal(m, b)
}
func (m *StartWorkflowRequest) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_StartWorkflowRequest.Marshal(b, m, deterministic)
}
func (dst *StartWorkflowRequest) XXX_Merge(src proto.Message) {
	xxx_messageInfo_StartWorkflowRequest.Merge(dst, src)
}
func (m *StartWorkflowRequest) XXX_Size() int {
	return xxx_messageInfo_StartWorkflowRequest.Size(m)
}
func (m *StartWorkflowRequest) XXX_DiscardUnknown() {
	xxx_messageInfo_StartWorkflowRequest.DiscardUnknown(m)
}

var xxx_messageInfo_StartWorkflowRequest proto.InternalMessageInfo

func (m *StartWorkflowRequest) GetName() string {
	if m != nil {
		return m.Name
	}
	return ""
}

func (m *StartWorkflowRequest) GetVersion() int32 {
	if m != nil {
		return m.Version
	}
	return 0
}

func (m *StartWorkflowRequest) GetCorrelationId() string {
	if m != nil {
		return m.CorrelationId
	}
	return ""
}

func (m *StartWorkflowRequest) GetInput() map[string]*_struct.Value {
	if m != nil {
		return m.Input
	}
	return nil
}

func (m *StartWorkflowRequest) GetTaskToDomain() map[string]string {
	if m != nil {
		return m.TaskToDomain
	}
	return nil
}

func (m *StartWorkflowRequest) GetWorkflowDef() *WorkflowDef {
	if m != nil {
		return m.WorkflowDef
	}
	return nil
}

func init() {
	proto.RegisterType((*StartWorkflowRequest)(nil), "conductor.proto.StartWorkflowRequest")
	proto.RegisterMapType((map[string]*_struct.Value)(nil), "conductor.proto.StartWorkflowRequest.InputEntry")
	proto.RegisterMapType((map[string]string)(nil), "conductor.proto.StartWorkflowRequest.TaskToDomainEntry")
}

func init() {
	proto.RegisterFile("model/startworkflowrequest.proto", fileDescriptor_startworkflowrequest_57b778443ff5f3ba)
}

var fileDescriptor_startworkflowrequest_57b778443ff5f3ba = []byte{
	// 396 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xff, 0x8c, 0x92, 0x51, 0xab, 0xd3, 0x30,
	0x14, 0x80, 0xe9, 0xed, 0xed, 0x95, 0x9b, 0x5e, 0xaf, 0x1a, 0x2e, 0xd7, 0x32, 0xf7, 0x50, 0x04,
	0xa1, 0x0f, 0x92, 0xca, 0x7c, 0x50, 0xf6, 0x32, 0x18, 0x53, 0xd8, 0xdb, 0xa8, 0x43, 0x41, 0x90,
	0xd2, 0xa6, 0x69, 0x0d, 0x6d, 0x73, 0xb6, 0x34, 0xdd, 0xdc, 0x1f, 0xf6, 0x77, 0x48, 0xd3, 0xd6,
	0x95, 0x6d, 0x0f, 0xf7, 0x2d, 0xe7, 0x24, 0xdf, 0x97, 0x93, 0x73, 0x82, 0xdc, 0x12, 0x12, 0x56,
	0xf8, 0x95, 0x8a, 0xa4, 0xda, 0x83, 0xcc, 0xd3, 0x02, 0xf6, 0x92, 0x6d, 0x6b, 0x56, 0x29, 0xb2,
	0x91, 0xa0, 0x00, 0xbf, 0xa0, 0x20, 0x92, 0x9a, 0x2a, 0x90, 0x6d, 0x62, 0xf4, 0xba, 0x45, 0xfa,
	0xd3, 0x09, 0x4b, 0xbb, 0x8d, 0x71, 0x06, 0x90, 0x15, 0xcc, 0xd7, 0x51, 0x5c, 0xa7, 0x7e, 0xa5,
	0x64, 0x4d, 0x3b, 0xcf, 0xdb, 0xbf, 0x26, 0x7a, 0xf8, 0xd6, 0x5c, 0xf3, 0xa3, 0x03, 0x83, 0xf6,
	0x1a, 0x8c, 0xd1, 0xb5, 0x88, 0x4a, 0xe6, 0x18, 0xae, 0xe1, 0xdd, 0x06, 0x7a, 0x8d, 0x1d, 0xf4,
	0x6c, 0xc7, 0x64, 0xc5, 0x41, 0x38, 0x57, 0xae, 0xe1, 0x59, 0x41, 0x1f, 0xe2, 0x77, 0xe8, 0x9e,
	0x82, 0x94, 0xac, 0x88, 0x14, 0x07, 0x11, 0xf2, 0xc4, 0x31, 0x35, 0xf7, 0x7c, 0x90, 0x5d, 0x26,
	0xf8, 0x2b, 0xb2, 0xb8, 0xd8, 0xd4, 0xca, 0xb9, 0x76, 0x4d, 0xcf, 0x9e, 0x7c, 0x20, 0x27, 0xaf,
	0x20, 0x97, 0x4a, 0x21, 0xcb, 0x06, 0xf9, 0x22, 0x94, 0x3c, 0x04, 0x2d, 0x8e, 0x7f, 0xa1, 0x7b,
	0x15, 0x55, 0x79, 0xa8, 0x20, 0x4c, 0xa0, 0x8c, 0xb8, 0x70, 0x2c, 0x2d, 0xfc, 0xf4, 0x34, 0xe1,
	0x3a, 0xaa, 0xf2, 0x35, 0x2c, 0x34, 0xd9, 0x7a, 0xef, 0xd4, 0x20, 0x85, 0x67, 0xe8, 0xae, 0xef,
	0x63, 0x98, 0xb0, 0xd4, 0xb9, 0x71, 0x0d, 0xcf, 0x9e, 0x8c, 0xcf, 0xe4, 0xbd, 0x77, 0xc1, 0xd2,
	0xc0, 0xde, 0x1f, 0x83, 0xd1, 0x0a, 0xa1, 0x63, 0xd1, 0xf8, 0x25, 0x32, 0x73, 0x76, 0xe8, 0x3a,
	0xd9, 0x2c, 0xf1, 0x7b, 0x64, 0xed, 0xa2, 0xa2, 0x66, 0xba, 0x8d, 0xf6, 0xe4, 0x91, 0xb4, 0x33,
	0x22, 0xfd, 0x8c, 0xc8, 0xf7, 0x66, 0x37, 0x68, 0x0f, 0x4d, 0xaf, 0x3e, 0x1b, 0xa3, 0x19, 0x7a,
	0x75, 0x56, 0xf5, 0x05, 0xf1, 0xc3, 0x50, 0x7c, 0x3b, 0x10, 0xcc, 0xb7, 0xe8, 0x0d, 0x85, 0x92,
	0x08, 0xa6, 0xd2, 0x82, 0xff, 0x39, 0x7d, 0xca, 0xfc, 0xf1, 0x52, 0xa3, 0x56, 0xf1, 0xcf, 0x69,
	0xc6, 0xd5, 0xef, 0x3a, 0x26, 0x14, 0x4a, 0xbf, 0x63, 0xfd, 0xff, 0xac, 0x4f, 0x0b, 0xce, 0x84,
	0xf2, 0x33, 0xc8, 0xe4, 0x86, 0x0e, 0xf2, 0xfa, 0x2f, 0xc6, 0x37, 0x5a, 0xfd, 0xf1, 0x5f, 0x00,
	0x00, 0x00, 0xff, 0xff, 0x26, 0x3f, 0xa7, 0x2d, 0xce, 0x02, 0x00, 0x00,
}
