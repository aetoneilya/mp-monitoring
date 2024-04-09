from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ApiEndpointsQueryResponse200ItemType1ColumnsItem")


@_attrs_define
class ApiEndpointsQueryResponse200ItemType1ColumnsItem:
    """
    Example:
        [{'text': 'Time', 'type': 'time'}, {'text': 'Country', 'type': 'string'}, {'text': 'Value', 'type': 'number'}]

    Attributes:
        text (str):
        type (Union[Unset, str]):
    """

    text: str
    type: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        text = self.text

        type = self.type

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "text": text,
            }
        )
        if type is not UNSET:
            field_dict["type"] = type

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        text = d.pop("text")

        type = d.pop("type", UNSET)

        api_endpoints_query_response_200_item_type_1_columns_item = cls(
            text=text,
            type=type,
        )

        api_endpoints_query_response_200_item_type_1_columns_item.additional_properties = d
        return api_endpoints_query_response_200_item_type_1_columns_item

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
