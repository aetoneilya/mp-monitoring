from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.api_endpoints_list_metric_payload_options_body import ApiEndpointsListMetricPayloadOptionsBody
from ...models.api_endpoints_list_metric_payload_options_response_200_item import (
    ApiEndpointsListMetricPayloadOptionsResponse200Item,
)
from ...types import Response


def _get_kwargs(
    *,
    body: ApiEndpointsListMetricPayloadOptionsBody,
) -> Dict[str, Any]:
    headers: Dict[str, Any] = {}

    _kwargs: Dict[str, Any] = {
        "method": "post",
        "url": "/metric-payload-options",
    }

    _body = body.to_dict()

    _kwargs["json"] = _body
    headers["Content-Type"] = "application/json"

    _kwargs["headers"] = headers
    return _kwargs


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["ApiEndpointsListMetricPayloadOptionsResponse200Item"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = ApiEndpointsListMetricPayloadOptionsResponse200Item.from_dict(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["ApiEndpointsListMetricPayloadOptionsResponse200Item"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsListMetricPayloadOptionsBody,
) -> Response[List["ApiEndpointsListMetricPayloadOptionsResponse200Item"]]:
    """List the available payload options.

     When the payload `type` is `select` or `multi-select` and the payload `options` configuration is
    empty, expanding the drop-down menu will trigger this API. The request body will carry the current
    metric and payload.

    Args:
        body (ApiEndpointsListMetricPayloadOptionsBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['ApiEndpointsListMetricPayloadOptionsResponse200Item']]
    """

    kwargs = _get_kwargs(
        body=body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsListMetricPayloadOptionsBody,
) -> Optional[List["ApiEndpointsListMetricPayloadOptionsResponse200Item"]]:
    """List the available payload options.

     When the payload `type` is `select` or `multi-select` and the payload `options` configuration is
    empty, expanding the drop-down menu will trigger this API. The request body will carry the current
    metric and payload.

    Args:
        body (ApiEndpointsListMetricPayloadOptionsBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['ApiEndpointsListMetricPayloadOptionsResponse200Item']
    """

    return sync_detailed(
        client=client,
        body=body,
    ).parsed


async def asyncio_detailed(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsListMetricPayloadOptionsBody,
) -> Response[List["ApiEndpointsListMetricPayloadOptionsResponse200Item"]]:
    """List the available payload options.

     When the payload `type` is `select` or `multi-select` and the payload `options` configuration is
    empty, expanding the drop-down menu will trigger this API. The request body will carry the current
    metric and payload.

    Args:
        body (ApiEndpointsListMetricPayloadOptionsBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['ApiEndpointsListMetricPayloadOptionsResponse200Item']]
    """

    kwargs = _get_kwargs(
        body=body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsListMetricPayloadOptionsBody,
) -> Optional[List["ApiEndpointsListMetricPayloadOptionsResponse200Item"]]:
    """List the available payload options.

     When the payload `type` is `select` or `multi-select` and the payload `options` configuration is
    empty, expanding the drop-down menu will trigger this API. The request body will carry the current
    metric and payload.

    Args:
        body (ApiEndpointsListMetricPayloadOptionsBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['ApiEndpointsListMetricPayloadOptionsResponse200Item']
    """

    return (
        await asyncio_detailed(
            client=client,
            body=body,
        )
    ).parsed
