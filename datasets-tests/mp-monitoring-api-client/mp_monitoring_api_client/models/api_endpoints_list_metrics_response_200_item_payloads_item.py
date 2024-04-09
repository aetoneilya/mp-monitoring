from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.api_endpoints_list_metrics_response_200_item_payloads_item_type import (
    ApiEndpointsListMetricsResponse200ItemPayloadsItemType,
)
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.api_endpoints_list_metrics_response_200_item_payloads_item_options_item import (
        ApiEndpointsListMetricsResponse200ItemPayloadsItemOptionsItem,
    )


T = TypeVar("T", bound="ApiEndpointsListMetricsResponse200ItemPayloadsItem")


@_attrs_define
class ApiEndpointsListMetricsResponse200ItemPayloadsItem:
    """
    Attributes:
        name (str): The name of the payload.
        label (Union[Unset, str]): The label of the payload. If the value is empty, use the name as the label.
        type (Union[Unset, ApiEndpointsListMetricsResponse200ItemPayloadsItemType]): If the value is select, the UI of
            the payload is a radio box.
            If the value is multi-select, the UI of the payload is a multi selection box.
            if the value is input, the UI of the payload is an input box.
            if the value is textarea, the UI of the payload is a multiline input box.
            The default is input. Default: ApiEndpointsListMetricsResponse200ItemPayloadsItemType.INPUT.
        placeholder (Union[Unset, str]): Input box / selection box prompt information.
        reload_metric (Union[Unset, bool]): Whether to overload the metrics API after modifying the value of the
            payload. Default: False.
        width (Union[Unset, int]): Set the input / selection box width to a multiple of 8px.
        options (Union[Unset, List['ApiEndpointsListMetricsResponse200ItemPayloadsItemOptionsItem']]): If the payload
            type is select / multi-select, the list is the configuration of the option list.
    """

    name: str
    label: Union[Unset, str] = UNSET
    type: Union[Unset, ApiEndpointsListMetricsResponse200ItemPayloadsItemType] = (
        ApiEndpointsListMetricsResponse200ItemPayloadsItemType.INPUT
    )
    placeholder: Union[Unset, str] = UNSET
    reload_metric: Union[Unset, bool] = False
    width: Union[Unset, int] = UNSET
    options: Union[Unset, List["ApiEndpointsListMetricsResponse200ItemPayloadsItemOptionsItem"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        name = self.name

        label = self.label

        type: Union[Unset, str] = UNSET
        if not isinstance(self.type, Unset):
            type = self.type.value

        placeholder = self.placeholder

        reload_metric = self.reload_metric

        width = self.width

        options: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.options, Unset):
            options = []
            for options_item_data in self.options:
                options_item = options_item_data.to_dict()
                options.append(options_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "name": name,
            }
        )
        if label is not UNSET:
            field_dict["label"] = label
        if type is not UNSET:
            field_dict["type"] = type
        if placeholder is not UNSET:
            field_dict["placeholder"] = placeholder
        if reload_metric is not UNSET:
            field_dict["reloadMetric"] = reload_metric
        if width is not UNSET:
            field_dict["width"] = width
        if options is not UNSET:
            field_dict["options"] = options

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.api_endpoints_list_metrics_response_200_item_payloads_item_options_item import (
            ApiEndpointsListMetricsResponse200ItemPayloadsItemOptionsItem,
        )

        d = src_dict.copy()
        name = d.pop("name")

        label = d.pop("label", UNSET)

        _type = d.pop("type", UNSET)
        type: Union[Unset, ApiEndpointsListMetricsResponse200ItemPayloadsItemType]
        if isinstance(_type, Unset):
            type = UNSET
        else:
            type = ApiEndpointsListMetricsResponse200ItemPayloadsItemType(_type)

        placeholder = d.pop("placeholder", UNSET)

        reload_metric = d.pop("reloadMetric", UNSET)

        width = d.pop("width", UNSET)

        options = []
        _options = d.pop("options", UNSET)
        for options_item_data in _options or []:
            options_item = ApiEndpointsListMetricsResponse200ItemPayloadsItemOptionsItem.from_dict(options_item_data)

            options.append(options_item)

        api_endpoints_list_metrics_response_200_item_payloads_item = cls(
            name=name,
            label=label,
            type=type,
            placeholder=placeholder,
            reload_metric=reload_metric,
            width=width,
            options=options,
        )

        api_endpoints_list_metrics_response_200_item_payloads_item.additional_properties = d
        return api_endpoints_list_metrics_response_200_item_payloads_item

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
