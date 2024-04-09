from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ApiEndpointsQueryResponse200ItemType0")


@_attrs_define
class ApiEndpointsQueryResponse200ItemType0:
    """timeseries case

    Attributes:
        target (str):  Example: upper_25.
        data_points (Union[Unset, List[List[float]]]):  Example: [[2.5, 1557385723416], [3.5, 1557385731634]].
    """

    target: str
    data_points: Union[Unset, List[List[float]]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        target = self.target

        data_points: Union[Unset, List[List[float]]] = UNSET
        if not isinstance(self.data_points, Unset):
            data_points = []
            for data_points_item_data in self.data_points:
                data_points_item = data_points_item_data

                data_points.append(data_points_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "target": target,
            }
        )
        if data_points is not UNSET:
            field_dict["dataPoints"] = data_points

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        target = d.pop("target")

        data_points = []
        _data_points = d.pop("dataPoints", UNSET)
        for data_points_item_data in _data_points or []:
            data_points_item = cast(List[float], data_points_item_data)

            data_points.append(data_points_item)

        api_endpoints_query_response_200_item_type_0 = cls(
            target=target,
            data_points=data_points,
        )

        api_endpoints_query_response_200_item_type_0.additional_properties = d
        return api_endpoints_query_response_200_item_type_0

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
