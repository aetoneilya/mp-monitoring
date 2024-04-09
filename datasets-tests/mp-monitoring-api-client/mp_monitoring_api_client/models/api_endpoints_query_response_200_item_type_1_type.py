from enum import Enum


class ApiEndpointsQueryResponse200ItemType1Type(str, Enum):
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
