from enum import Enum


class StoreMetricsResponseStatus(str, Enum):
    ERROR = "error"
    SUCCESSFUL = "successful"

    def __str__(self) -> str:
        return str(self.value)
