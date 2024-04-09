from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.api_endpoints_variable_body_payload import ApiEndpointsVariableBodyPayload
    from ..models.api_endpoints_variable_body_range import ApiEndpointsVariableBodyRange


T = TypeVar("T", bound="ApiEndpointsVariableBody")


@_attrs_define
class ApiEndpointsVariableBody:
    """
    Attributes:
        payload (Union[Unset, ApiEndpointsVariableBodyPayload]):
        range_ (Union[Unset, ApiEndpointsVariableBodyRange]):
    """

    payload: Union[Unset, "ApiEndpointsVariableBodyPayload"] = UNSET
    range_: Union[Unset, "ApiEndpointsVariableBodyRange"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        payload: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.payload, Unset):
            payload = self.payload.to_dict()

        range_: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.range_, Unset):
            range_ = self.range_.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if payload is not UNSET:
            field_dict["payload"] = payload
        if range_ is not UNSET:
            field_dict["range"] = range_

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.api_endpoints_variable_body_payload import ApiEndpointsVariableBodyPayload
        from ..models.api_endpoints_variable_body_range import ApiEndpointsVariableBodyRange

        d = src_dict.copy()
        _payload = d.pop("payload", UNSET)
        payload: Union[Unset, ApiEndpointsVariableBodyPayload]
        if isinstance(_payload, Unset):
            payload = UNSET
        else:
            payload = ApiEndpointsVariableBodyPayload.from_dict(_payload)

        _range_ = d.pop("range", UNSET)
        range_: Union[Unset, ApiEndpointsVariableBodyRange]
        if isinstance(_range_, Unset):
            range_ = UNSET
        else:
            range_ = ApiEndpointsVariableBodyRange.from_dict(_range_)

        api_endpoints_variable_body = cls(
            payload=payload,
            range_=range_,
        )

        api_endpoints_variable_body.additional_properties = d
        return api_endpoints_variable_body

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> Any:
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: Any) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
