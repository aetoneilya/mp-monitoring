from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.api_endpoints_list_metrics_response_200_item_payloads_item import (
        ApiEndpointsListMetricsResponse200ItemPayloadsItem,
    )


T = TypeVar("T", bound="ApiEndpointsListMetricsResponse200Item")


@_attrs_define
class ApiEndpointsListMetricsResponse200Item:
    """
    Attributes:
        value (str): The value of the option.
        payloads (List['ApiEndpointsListMetricsResponse200ItemPayloadsItem']): Configuration parameters of the payload.
        label (Union[Unset, str]): If the value is empty, use the value as the label
    """

    value: str
    payloads: List["ApiEndpointsListMetricsResponse200ItemPayloadsItem"]
    label: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        value = self.value

        payloads = []
        for payloads_item_data in self.payloads:
            payloads_item = payloads_item_data.to_dict()
            payloads.append(payloads_item)

        label = self.label

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "value": value,
                "payloads": payloads,
            }
        )
        if label is not UNSET:
            field_dict["label"] = label

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.api_endpoints_list_metrics_response_200_item_payloads_item import (
            ApiEndpointsListMetricsResponse200ItemPayloadsItem,
        )

        d = src_dict.copy()
        value = d.pop("value")

        payloads = []
        _payloads = d.pop("payloads")
        for payloads_item_data in _payloads:
            payloads_item = ApiEndpointsListMetricsResponse200ItemPayloadsItem.from_dict(payloads_item_data)

            payloads.append(payloads_item)

        label = d.pop("label", UNSET)

        api_endpoints_list_metrics_response_200_item = cls(
            value=value,
            payloads=payloads,
            label=label,
        )

        api_endpoints_list_metrics_response_200_item.additional_properties = d
        return api_endpoints_list_metrics_response_200_item

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
