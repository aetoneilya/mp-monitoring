from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.store_metrics_response_status import StoreMetricsResponseStatus
from ..types import UNSET, Unset

T = TypeVar("T", bound="StoreMetricsResponse")


@_attrs_define
class StoreMetricsResponse:
    """Response received after storing metric data points.

    Attributes:
        status (Union[Unset, StoreMetricsResponseStatus]): Indicates the result of the operation.
        processed (Union[Unset, int]): The number of metric data points that were processed.
    """

    status: Union[Unset, StoreMetricsResponseStatus] = UNSET
    processed: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        status: Union[Unset, str] = UNSET
        if not isinstance(self.status, Unset):
            status = self.status.value

        processed = self.processed

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if status is not UNSET:
            field_dict["status"] = status
        if processed is not UNSET:
            field_dict["processed"] = processed

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _status = d.pop("status", UNSET)
        status: Union[Unset, StoreMetricsResponseStatus]
        if isinstance(_status, Unset):
            status = UNSET
        else:
            status = StoreMetricsResponseStatus(_status)

        processed = d.pop("processed", UNSET)

        store_metrics_response = cls(
            status=status,
            processed=processed,
        )

        store_metrics_response.additional_properties = d
        return store_metrics_response

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
