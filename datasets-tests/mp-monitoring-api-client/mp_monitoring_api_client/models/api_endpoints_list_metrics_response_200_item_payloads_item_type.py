from enum import Enum


class ApiEndpointsListMetricsResponse200ItemPayloadsItemType(str, Enum):
    INPUT = "input"
    MULTI_SELECT = "multi-select"
    SELECT = "select"
    TEXTAREA = "textarea"

    def __str__(self) -> str:
        return str(self.value)
