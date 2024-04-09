from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.api_endpoints_query_body_targets_item_payload import ApiEndpointsQueryBodyTargetsItemPayload


T = TypeVar("T", bound="ApiEndpointsQueryBodyTargetsItem")


@_attrs_define
class ApiEndpointsQueryBodyTargetsItem:
    """
    Attributes:
        target (str):  Example: upper_25.
        ref_id (Union[Unset, str]):
        payload (Union[Unset, ApiEndpointsQueryBodyTargetsItemPayload]): arbitrary "additional data" the user can pass
            in
    """

    target: str
    ref_id: Union[Unset, str] = UNSET
    payload: Union[Unset, "ApiEndpointsQueryBodyTargetsItemPayload"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        target = self.target

        ref_id = self.ref_id

        payload: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.payload, Unset):
            payload = self.payload.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "target": target,
            }
        )
        if ref_id is not UNSET:
            field_dict["refId"] = ref_id
        if payload is not UNSET:
            field_dict["payload"] = payload

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.api_endpoints_query_body_targets_item_payload import ApiEndpointsQueryBodyTargetsItemPayload

        d = src_dict.copy()
        target = d.pop("target")

        ref_id = d.pop("refId", UNSET)

        _payload = d.pop("payload", UNSET)
        payload: Union[Unset, ApiEndpointsQueryBodyTargetsItemPayload]
        if isinstance(_payload, Unset):
            payload = UNSET
        else:
            payload = ApiEndpointsQueryBodyTargetsItemPayload.from_dict(_payload)

        api_endpoints_query_body_targets_item = cls(
            target=target,
            ref_id=ref_id,
            payload=payload,
        )

        api_endpoints_query_body_targets_item.additional_properties = d
        return api_endpoints_query_body_targets_item

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
