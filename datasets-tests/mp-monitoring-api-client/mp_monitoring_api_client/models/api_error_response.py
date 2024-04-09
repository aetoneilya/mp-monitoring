from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ApiErrorResponse")


@_attrs_define
class ApiErrorResponse:
    """
    Attributes:
        description (str):
        code (Union[Unset, str]):
        exception_name (Union[Unset, str]):
        exception_message (Union[Unset, str]):
        stacktrace (Union[Unset, List[str]]):
    """

    description: str
    code: Union[Unset, str] = UNSET
    exception_name: Union[Unset, str] = UNSET
    exception_message: Union[Unset, str] = UNSET
    stacktrace: Union[Unset, List[str]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        description = self.description

        code = self.code

        exception_name = self.exception_name

        exception_message = self.exception_message

        stacktrace: Union[Unset, List[str]] = UNSET
        if not isinstance(self.stacktrace, Unset):
            stacktrace = self.stacktrace

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update(
            {
                "description": description,
            }
        )
        if code is not UNSET:
            field_dict["code"] = code
        if exception_name is not UNSET:
            field_dict["exceptionName"] = exception_name
        if exception_message is not UNSET:
            field_dict["exceptionMessage"] = exception_message
        if stacktrace is not UNSET:
            field_dict["stacktrace"] = stacktrace

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        description = d.pop("description")

        code = d.pop("code", UNSET)

        exception_name = d.pop("exceptionName", UNSET)

        exception_message = d.pop("exceptionMessage", UNSET)

        stacktrace = cast(List[str], d.pop("stacktrace", UNSET))

        api_error_response = cls(
            description=description,
            code=code,
            exception_name=exception_name,
            exception_message=exception_message,
            stacktrace=stacktrace,
        )

        api_error_response.additional_properties = d
        return api_error_response

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
