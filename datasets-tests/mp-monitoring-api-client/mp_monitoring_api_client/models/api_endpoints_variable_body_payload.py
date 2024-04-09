from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.api_endpoints_variable_body_payload_variables import ApiEndpointsVariableBodyPayloadVariables


T = TypeVar("T", bound="ApiEndpointsVariableBodyPayload")


@_attrs_define
class ApiEndpointsVariableBodyPayload:
    """
    Attributes:
        target (str):
        variables (Union[Unset, ApiEndpointsVariableBodyPayloadVariables]):
    """

    target: str
    variables: Union[Unset, "ApiEndpointsVariableBodyPayloadVariables"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        target = self.target

        variables: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.variables, Unset):
            variables = self.variables.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "target": target,
            }
        )
        if variables is not UNSET:
            field_dict["variables"] = variables

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.api_endpoints_variable_body_payload_variables import ApiEndpointsVariableBodyPayloadVariables

        d = src_dict.copy()
        target = d.pop("target")

        _variables = d.pop("variables", UNSET)
        variables: Union[Unset, ApiEndpointsVariableBodyPayloadVariables]
        if isinstance(_variables, Unset):
            variables = UNSET
        else:
            variables = ApiEndpointsVariableBodyPayloadVariables.from_dict(_variables)

        api_endpoints_variable_body_payload = cls(
            target=target,
            variables=variables,
        )

        api_endpoints_variable_body_payload.additional_properties = d
        return api_endpoints_variable_body_payload

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
