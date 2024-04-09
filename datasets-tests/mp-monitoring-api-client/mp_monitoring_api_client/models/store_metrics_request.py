from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.metric_dto import MetricDto


T = TypeVar("T", bound="StoreMetricsRequest")


@_attrs_define
class StoreMetricsRequest:
    """
    Attributes:
        metrics (Union[Unset, MetricDto]):
    """

    metrics: Union[Unset, "MetricDto"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        metrics: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.metrics, Unset):
            metrics = self.metrics.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if metrics is not UNSET:
            field_dict["metrics"] = metrics

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.metric_dto import MetricDto

        d = src_dict.copy()
        _metrics = d.pop("metrics", UNSET)
        metrics: Union[Unset, MetricDto]
        if isinstance(_metrics, Unset):
            metrics = UNSET
        else:
            metrics = MetricDto.from_dict(_metrics)

        store_metrics_request = cls(
            metrics=metrics,
        )

        store_metrics_request.additional_properties = d
        return store_metrics_request

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
