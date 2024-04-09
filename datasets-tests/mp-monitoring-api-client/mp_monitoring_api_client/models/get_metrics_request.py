from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.metadata_dto import MetadataDto


T = TypeVar("T", bound="GetMetricsRequest")


@_attrs_define
class GetMetricsRequest:
    """
    Attributes:
        metadata (MetadataDto): Metadata associated with the metric data, represented as a map of label names to values.
        from_ (Union[Unset, int]): The start of the time range to retrieve data from in Unix milliseconds.
        to (Union[Unset, int]): The end of the time range to retrieve data from in Unix milliseconds.
    """

    metadata: "MetadataDto"
    from_: Union[Unset, int] = UNSET
    to: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        metadata = self.metadata.to_dict()

        from_ = self.from_

        to = self.to

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "metadata": metadata,
            }
        )
        if from_ is not UNSET:
            field_dict["from"] = from_
        if to is not UNSET:
            field_dict["to"] = to

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.metadata_dto import MetadataDto

        d = src_dict.copy()
        metadata = MetadataDto.from_dict(d.pop("metadata"))

        from_ = d.pop("from", UNSET)

        to = d.pop("to", UNSET)

        get_metrics_request = cls(
            metadata=metadata,
            from_=from_,
            to=to,
        )

        get_metrics_request.additional_properties = d
        return get_metrics_request

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
