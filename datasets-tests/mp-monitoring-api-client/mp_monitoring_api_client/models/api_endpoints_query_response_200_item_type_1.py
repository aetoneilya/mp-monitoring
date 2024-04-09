from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.api_endpoints_query_response_200_item_type_1_type import ApiEndpointsQueryResponse200ItemType1Type

if TYPE_CHECKING:
    from ..models.api_endpoints_query_response_200_item_type_1_columns_item import (
        ApiEndpointsQueryResponse200ItemType1ColumnsItem,
    )


T = TypeVar("T", bound="ApiEndpointsQueryResponse200ItemType1")


@_attrs_define
class ApiEndpointsQueryResponse200ItemType1:
    """table case

    Attributes:
        type (ApiEndpointsQueryResponse200ItemType1Type):
        columns (List['ApiEndpointsQueryResponse200ItemType1ColumnsItem']):
        rows (List[Union[float, str]]):  Example: [[1557385723416, 'SE', 123], [1557385731634, 'SE', 456]].
    """

    type: ApiEndpointsQueryResponse200ItemType1Type
    columns: List["ApiEndpointsQueryResponse200ItemType1ColumnsItem"]
    rows: List[Union[float, str]]
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        type = self.type.value

        columns = []
        for columns_item_data in self.columns:
            columns_item = columns_item_data.to_dict()
            columns.append(columns_item)

        rows = []
        for rows_item_data in self.rows:
            rows_item: Union[float, str]
            rows_item = rows_item_data
            rows.append(rows_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "type": type,
                "columns": columns,
                "rows": rows,
            }
        )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.api_endpoints_query_response_200_item_type_1_columns_item import (
            ApiEndpointsQueryResponse200ItemType1ColumnsItem,
        )

        d = src_dict.copy()
        type = ApiEndpointsQueryResponse200ItemType1Type(d.pop("type"))

        columns = []
        _columns = d.pop("columns")
        for columns_item_data in _columns:
            columns_item = ApiEndpointsQueryResponse200ItemType1ColumnsItem.from_dict(columns_item_data)

            columns.append(columns_item)

        rows = []
        _rows = d.pop("rows")
        for rows_item_data in _rows:

            def _parse_rows_item(data: object) -> Union[float, str]:
                return cast(Union[float, str], data)

            rows_item = _parse_rows_item(rows_item_data)

            rows.append(rows_item)

        api_endpoints_query_response_200_item_type_1 = cls(
            type=type,
            columns=columns,
            rows=rows,
        )

        api_endpoints_query_response_200_item_type_1.additional_properties = d
        return api_endpoints_query_response_200_item_type_1

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
