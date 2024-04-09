from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.api_endpoints_query_body_adhoc_filters_item import ApiEndpointsQueryBodyAdhocFiltersItem
    from ..models.api_endpoints_query_body_range import ApiEndpointsQueryBodyRange
    from ..models.api_endpoints_query_body_scoped_vars import ApiEndpointsQueryBodyScopedVars
    from ..models.api_endpoints_query_body_targets_item import ApiEndpointsQueryBodyTargetsItem
    from ..models.raw_time_frame import RawTimeFrame


T = TypeVar("T", bound="ApiEndpointsQueryBody")


@_attrs_define
class ApiEndpointsQueryBody:
    """
    Attributes:
        panel_id (Union[Unset, float, str]):
        range_ (Union[Unset, ApiEndpointsQueryBodyRange]):
        range_raw (Union[Unset, RawTimeFrame]):
        interval (Union[Unset, str]):  Example: 30s.
        interval_ms (Union[Unset, float]):  Example: 5500.
        max_data_points (Union[Unset, float]):  Example: 50.
        targets (Union[Unset, List['ApiEndpointsQueryBodyTargetsItem']]):
        scoped_vars (Union[Unset, ApiEndpointsQueryBodyScopedVars]):  Example: {'__interval': {'text': '1s', 'value':
            '1s'}, '__interval_ms': {'text': 1000, 'value': 1000}}.
        adhoc_filters (Union[Unset, List['ApiEndpointsQueryBodyAdhocFiltersItem']]):
    """

    panel_id: Union[Unset, float, str] = UNSET
    range_: Union[Unset, "ApiEndpointsQueryBodyRange"] = UNSET
    range_raw: Union[Unset, "RawTimeFrame"] = UNSET
    interval: Union[Unset, str] = UNSET
    interval_ms: Union[Unset, float] = UNSET
    max_data_points: Union[Unset, float] = UNSET
    targets: Union[Unset, List["ApiEndpointsQueryBodyTargetsItem"]] = UNSET
    scoped_vars: Union[Unset, "ApiEndpointsQueryBodyScopedVars"] = UNSET
    adhoc_filters: Union[Unset, List["ApiEndpointsQueryBodyAdhocFiltersItem"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        panel_id: Union[Unset, float, str]
        if isinstance(self.panel_id, Unset):
            panel_id = UNSET
        else:
            panel_id = self.panel_id

        range_: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.range_, Unset):
            range_ = self.range_.to_dict()

        range_raw: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.range_raw, Unset):
            range_raw = self.range_raw.to_dict()

        interval = self.interval

        interval_ms = self.interval_ms

        max_data_points = self.max_data_points

        targets: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.targets, Unset):
            targets = []
            for targets_item_data in self.targets:
                targets_item = targets_item_data.to_dict()
                targets.append(targets_item)

        scoped_vars: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.scoped_vars, Unset):
            scoped_vars = self.scoped_vars.to_dict()

        adhoc_filters: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.adhoc_filters, Unset):
            adhoc_filters = []
            for adhoc_filters_item_data in self.adhoc_filters:
                adhoc_filters_item = adhoc_filters_item_data.to_dict()
                adhoc_filters.append(adhoc_filters_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if panel_id is not UNSET:
            field_dict["panelId"] = panel_id
        if range_ is not UNSET:
            field_dict["range"] = range_
        if range_raw is not UNSET:
            field_dict["rangeRaw"] = range_raw
        if interval is not UNSET:
            field_dict["interval"] = interval
        if interval_ms is not UNSET:
            field_dict["intervalMs"] = interval_ms
        if max_data_points is not UNSET:
            field_dict["maxDataPoints"] = max_data_points
        if targets is not UNSET:
            field_dict["targets"] = targets
        if scoped_vars is not UNSET:
            field_dict["scopedVars"] = scoped_vars
        if adhoc_filters is not UNSET:
            field_dict["adhocFilters"] = adhoc_filters

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.api_endpoints_query_body_adhoc_filters_item import ApiEndpointsQueryBodyAdhocFiltersItem
        from ..models.api_endpoints_query_body_range import ApiEndpointsQueryBodyRange
        from ..models.api_endpoints_query_body_scoped_vars import ApiEndpointsQueryBodyScopedVars
        from ..models.api_endpoints_query_body_targets_item import ApiEndpointsQueryBodyTargetsItem
        from ..models.raw_time_frame import RawTimeFrame

        d = src_dict.copy()

        def _parse_panel_id(data: object) -> Union[Unset, float, str]:
            if isinstance(data, Unset):
                return data
            return cast(Union[Unset, float, str], data)

        panel_id = _parse_panel_id(d.pop("panelId", UNSET))

        _range_ = d.pop("range", UNSET)
        range_: Union[Unset, ApiEndpointsQueryBodyRange]
        if isinstance(_range_, Unset):
            range_ = UNSET
        else:
            range_ = ApiEndpointsQueryBodyRange.from_dict(_range_)

        _range_raw = d.pop("rangeRaw", UNSET)
        range_raw: Union[Unset, RawTimeFrame]
        if isinstance(_range_raw, Unset):
            range_raw = UNSET
        else:
            range_raw = RawTimeFrame.from_dict(_range_raw)

        interval = d.pop("interval", UNSET)

        interval_ms = d.pop("intervalMs", UNSET)

        max_data_points = d.pop("maxDataPoints", UNSET)

        targets = []
        _targets = d.pop("targets", UNSET)
        for targets_item_data in _targets or []:
            targets_item = ApiEndpointsQueryBodyTargetsItem.from_dict(targets_item_data)

            targets.append(targets_item)

        _scoped_vars = d.pop("scopedVars", UNSET)
        scoped_vars: Union[Unset, ApiEndpointsQueryBodyScopedVars]
        if isinstance(_scoped_vars, Unset):
            scoped_vars = UNSET
        else:
            scoped_vars = ApiEndpointsQueryBodyScopedVars.from_dict(_scoped_vars)

        adhoc_filters = []
        _adhoc_filters = d.pop("adhocFilters", UNSET)
        for adhoc_filters_item_data in _adhoc_filters or []:
            adhoc_filters_item = ApiEndpointsQueryBodyAdhocFiltersItem.from_dict(adhoc_filters_item_data)

            adhoc_filters.append(adhoc_filters_item)

        api_endpoints_query_body = cls(
            panel_id=panel_id,
            range_=range_,
            range_raw=range_raw,
            interval=interval,
            interval_ms=interval_ms,
            max_data_points=max_data_points,
            targets=targets,
            scoped_vars=scoped_vars,
            adhoc_filters=adhoc_filters,
        )

        api_endpoints_query_body.additional_properties = d
        return api_endpoints_query_body

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
