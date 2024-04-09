from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.api_endpoints_list_metric_payload_options_body_payload import (
        ApiEndpointsListMetricPayloadOptionsBodyPayload,
    )


T = TypeVar("T", bound="ApiEndpointsListMetricPayloadOptionsBody")


@_attrs_define
class ApiEndpointsListMetricPayloadOptionsBody:
    """
    Attributes:
        metric (str): Current metric.
        payload (ApiEndpointsListMetricPayloadOptionsBodyPayload): Current payload.
        name (str): The payload name of the option list needs to be obtained.
    """

    metric: str
    payload: "ApiEndpointsListMetricPayloadOptionsBodyPayload"
    name: str
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        metric = self.metric

        payload = self.payload.to_dict()

        name = self.name

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "metric": metric,
                "payload": payload,
                "name": name,
            }
        )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.api_endpoints_list_metric_payload_options_body_payload import (
            ApiEndpointsListMetricPayloadOptionsBodyPayload,
        )

        d = src_dict.copy()
        metric = d.pop("metric")

        payload = ApiEndpointsListMetricPayloadOptionsBodyPayload.from_dict(d.pop("payload"))

        name = d.pop("name")

        api_endpoints_list_metric_payload_options_body = cls(
            metric=metric,
            payload=payload,
            name=name,
        )

        api_endpoints_list_metric_payload_options_body.additional_properties = d
        return api_endpoints_list_metric_payload_options_body

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
