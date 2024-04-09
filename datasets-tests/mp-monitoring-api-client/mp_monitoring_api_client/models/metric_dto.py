from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.metadata_dto import MetadataDto
    from ..models.time_series_data_point_dto import TimeSeriesDataPointDto


T = TypeVar("T", bound="MetricDto")


@_attrs_define
class MetricDto:
    """
    Attributes:
        metadata (MetadataDto): Metadata associated with the metric data, represented as a map of label names to values.
        time_series (Union[Unset, List['TimeSeriesDataPointDto']]):
    """

    metadata: "MetadataDto"
    time_series: Union[Unset, List["TimeSeriesDataPointDto"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        metadata = self.metadata.to_dict()

        time_series: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.time_series, Unset):
            time_series = []
            for componentsschemas_time_series_dto_item_data in self.time_series:
                componentsschemas_time_series_dto_item = componentsschemas_time_series_dto_item_data.to_dict()
                time_series.append(componentsschemas_time_series_dto_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "metadata": metadata,
            }
        )
        if time_series is not UNSET:
            field_dict["timeSeries"] = time_series

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.metadata_dto import MetadataDto
        from ..models.time_series_data_point_dto import TimeSeriesDataPointDto

        d = src_dict.copy()
        metadata = MetadataDto.from_dict(d.pop("metadata"))

        time_series = []
        _time_series = d.pop("timeSeries", UNSET)
        for componentsschemas_time_series_dto_item_data in _time_series or []:
            componentsschemas_time_series_dto_item = TimeSeriesDataPointDto.from_dict(
                componentsschemas_time_series_dto_item_data
            )

            time_series.append(componentsschemas_time_series_dto_item)

        metric_dto = cls(
            metadata=metadata,
            time_series=time_series,
        )

        metric_dto.additional_properties = d
        return metric_dto

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
