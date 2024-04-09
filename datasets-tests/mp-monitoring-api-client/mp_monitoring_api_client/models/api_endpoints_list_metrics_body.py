from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.api_endpoints_list_metrics_body_payload import ApiEndpointsListMetricsBodyPayload


T = TypeVar("T", bound="ApiEndpointsListMetricsBody")


@_attrs_define
class ApiEndpointsListMetricsBody:
    """
    Attributes:
        metric (Union[Unset, str]): The currently selected Metric option.
        payload (Union[Unset, ApiEndpointsListMetricsBodyPayload]): The currently selected/entered payload options and
            values. Key is the name of the payload, and value is the value of the payload.
    """

    metric: Union[Unset, str] = UNSET
    payload: Union[Unset, "ApiEndpointsListMetricsBodyPayload"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        metric = self.metric

        payload: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.payload, Unset):
            payload = self.payload.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if metric is not UNSET:
            field_dict["metric"] = metric
        if payload is not UNSET:
            field_dict["payload"] = payload

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.api_endpoints_list_metrics_body_payload import ApiEndpointsListMetricsBodyPayload

        d = src_dict.copy()
        metric = d.pop("metric", UNSET)

        _payload = d.pop("payload", UNSET)
        payload: Union[Unset, ApiEndpointsListMetricsBodyPayload]
        if isinstance(_payload, Unset):
            payload = UNSET
        else:
            payload = ApiEndpointsListMetricsBodyPayload.from_dict(_payload)

        api_endpoints_list_metrics_body = cls(
            metric=metric,
            payload=payload,
        )

        api_endpoints_list_metrics_body.additional_properties = d
        return api_endpoints_list_metrics_body

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
