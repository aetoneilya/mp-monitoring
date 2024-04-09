from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.api_error_response import ApiErrorResponse
from ...models.get_metrics_request import GetMetricsRequest
from ...models.get_metrics_response import GetMetricsResponse
from ...types import Response


def _get_kwargs(
    project: str,
    *,
    body: GetMetricsRequest,
) -> Dict[str, Any]:
    headers: Dict[str, Any] = {}

    _kwargs: Dict[str, Any] = {
        "method": "post",
        "url": f"/metrics/{project}/find",
    }

    _body = body.to_dict()

    _kwargs["json"] = _body
    headers["Content-Type"] = "application/json"

    _kwargs["headers"] = headers
    return _kwargs


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[Union[ApiErrorResponse, GetMetricsResponse]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = GetMetricsResponse.from_dict(response.json())

        return response_200
    if response.status_code == HTTPStatus.BAD_REQUEST:
        response_400 = ApiErrorResponse.from_dict(response.json())

        return response_400
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[Union[ApiErrorResponse, GetMetricsResponse]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    project: str,
    *,
    client: Union[AuthenticatedClient, Client],
    body: GetMetricsRequest,
) -> Response[Union[ApiErrorResponse, GetMetricsResponse]]:
    """Get metrics by labels

    Args:
        project (str):
        body (GetMetricsRequest):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Union[ApiErrorResponse, GetMetricsResponse]]
    """

    kwargs = _get_kwargs(
        project=project,
        body=body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    project: str,
    *,
    client: Union[AuthenticatedClient, Client],
    body: GetMetricsRequest,
) -> Optional[Union[ApiErrorResponse, GetMetricsResponse]]:
    """Get metrics by labels

    Args:
        project (str):
        body (GetMetricsRequest):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Union[ApiErrorResponse, GetMetricsResponse]
    """

    return sync_detailed(
        project=project,
        client=client,
        body=body,
    ).parsed


async def asyncio_detailed(
    project: str,
    *,
    client: Union[AuthenticatedClient, Client],
    body: GetMetricsRequest,
) -> Response[Union[ApiErrorResponse, GetMetricsResponse]]:
    """Get metrics by labels

    Args:
        project (str):
        body (GetMetricsRequest):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Union[ApiErrorResponse, GetMetricsResponse]]
    """

    kwargs = _get_kwargs(
        project=project,
        body=body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    project: str,
    *,
    client: Union[AuthenticatedClient, Client],
    body: GetMetricsRequest,
) -> Optional[Union[ApiErrorResponse, GetMetricsResponse]]:
    """Get metrics by labels

    Args:
        project (str):
        body (GetMetricsRequest):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Union[ApiErrorResponse, GetMetricsResponse]
    """

    return (
        await asyncio_detailed(
            project=project,
            client=client,
            body=body,
        )
    ).parsed
