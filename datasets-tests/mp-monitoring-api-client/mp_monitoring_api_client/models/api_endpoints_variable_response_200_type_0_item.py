from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ApiEndpointsVariableResponse200Type0Item")


@_attrs_define
class ApiEndpointsVariableResponse200Type0Item:
    """
    Attributes:
        field_text (Union[Unset, str]):
        field_value (Union[Unset, str]):
    """

    field_text: Union[Unset, str] = UNSET
    field_value: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        field_text = self.field_text

        field_value = self.field_value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if field_text is not UNSET:
            field_dict["__text"] = field_text
        if field_value is not UNSET:
            field_dict["__value"] = field_value

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        field_text = d.pop("__text", UNSET)

        field_value = d.pop("__value", UNSET)

        api_endpoints_variable_response_200_type_0_item = cls(
            field_text=field_text,
            field_value=field_value,
        )

        api_endpoints_variable_response_200_type_0_item.additional_properties = d
        return api_endpoints_variable_response_200_type_0_item

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
