from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.dataframe_fields_item import DataframeFieldsItem


T = TypeVar("T", bound="Dataframe")


@_attrs_define
class Dataframe:
    """
    Attributes:
        fields (List['DataframeFieldsItem']):
    """

    fields: List["DataframeFieldsItem"]
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        fields = []
        for fields_item_data in self.fields:
            fields_item = fields_item_data.to_dict()
            fields.append(fields_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "fields": fields,
            }
        )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dataframe_fields_item import DataframeFieldsItem

        d = src_dict.copy()
        fields = []
        _fields = d.pop("fields")
        for fields_item_data in _fields:
            fields_item = DataframeFieldsItem.from_dict(fields_item_data)

            fields.append(fields_item)

        dataframe = cls(
            fields=fields,
        )

        dataframe.additional_properties = d
        return dataframe

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
