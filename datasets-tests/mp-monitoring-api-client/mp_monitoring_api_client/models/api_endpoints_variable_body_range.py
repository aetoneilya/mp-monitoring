import datetime
from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.raw_time_frame import RawTimeFrame


T = TypeVar("T", bound="ApiEndpointsVariableBodyRange")


@_attrs_define
class ApiEndpointsVariableBodyRange:
    """
    Attributes:
        from_ (Union[Unset, datetime.datetime]):
        to (Union[Unset, datetime.datetime]):
        raw (Union[Unset, RawTimeFrame]):
    """

    from_: Union[Unset, datetime.datetime] = UNSET
    to: Union[Unset, datetime.datetime] = UNSET
    raw: Union[Unset, "RawTimeFrame"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        from_: Union[Unset, str] = UNSET
        if not isinstance(self.from_, Unset):
            from_ = self.from_.isoformat()

        to: Union[Unset, str] = UNSET
        if not isinstance(self.to, Unset):
            to = self.to.isoformat()

        raw: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.raw, Unset):
            raw = self.raw.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if from_ is not UNSET:
            field_dict["from"] = from_
        if to is not UNSET:
            field_dict["to"] = to
        if raw is not UNSET:
            field_dict["raw"] = raw

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.raw_time_frame import RawTimeFrame

        d = src_dict.copy()
        _from_ = d.pop("from", UNSET)
        from_: Union[Unset, datetime.datetime]
        if isinstance(_from_, Unset):
            from_ = UNSET
        else:
            from_ = isoparse(_from_)

        _to = d.pop("to", UNSET)
        to: Union[Unset, datetime.datetime]
        if isinstance(_to, Unset):
            to = UNSET
        else:
            to = isoparse(_to)

        _raw = d.pop("raw", UNSET)
        raw: Union[Unset, RawTimeFrame]
        if isinstance(_raw, Unset):
            raw = UNSET
        else:
            raw = RawTimeFrame.from_dict(_raw)

        api_endpoints_variable_body_range = cls(
            from_=from_,
            to=to,
            raw=raw,
        )

        api_endpoints_variable_body_range.additional_properties = d
        return api_endpoints_variable_body_range

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
