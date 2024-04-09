from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ApiEndpointsQueryBodyAdhocFiltersItem")


@_attrs_define
class ApiEndpointsQueryBodyAdhocFiltersItem:
    """
    Attributes:
        key (Union[Unset, str]):  Example: City.
        operator (Union[Unset, str]):  Example: =.
        value (Union[Unset, str]):  Example: Berlin.
    """

    key: Union[Unset, str] = UNSET
    operator: Union[Unset, str] = UNSET
    value: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        key = self.key

        operator = self.operator

        value = self.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if key is not UNSET:
            field_dict["key"] = key
        if operator is not UNSET:
            field_dict["operator"] = operator
        if value is not UNSET:
            field_dict["value"] = value

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        key = d.pop("key", UNSET)

        operator = d.pop("operator", UNSET)

        value = d.pop("value", UNSET)

        api_endpoints_query_body_adhoc_filters_item = cls(
            key=key,
            operator=operator,
            value=value,
        )

        api_endpoints_query_body_adhoc_filters_item.additional_properties = d
        return api_endpoints_query_body_adhoc_filters_item

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
