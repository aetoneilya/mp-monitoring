from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ApiEndpointsListMetricPayloadOptionsResponse200Item")


@_attrs_define
class ApiEndpointsListMetricPayloadOptionsResponse200Item:
    """
    Attributes:
        value (str): The value of the option in the drop-down box.
        label (Union[Unset, str]): The label of the option in the drop-down box. If the value is empty, use the value as
            the label.
    """

    value: str
    label: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        value = self.value

        label = self.label

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "value": value,
            }
        )
        if label is not UNSET:
            field_dict["label"] = label

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        value = d.pop("value")

        label = d.pop("label", UNSET)

        api_endpoints_list_metric_payload_options_response_200_item = cls(
            value=value,
            label=label,
        )

        api_endpoints_list_metric_payload_options_response_200_item.additional_properties = d
        return api_endpoints_list_metric_payload_options_response_200_item

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
