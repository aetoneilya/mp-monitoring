from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.api_endpoints_query_body import ApiEndpointsQueryBody
from ...models.api_endpoints_query_response_200_item_type_0 import ApiEndpointsQueryResponse200ItemType0
from ...models.api_endpoints_query_response_200_item_type_1 import ApiEndpointsQueryResponse200ItemType1
from ...types import Response


def _get_kwargs(
    *,
    body: ApiEndpointsQueryBody,
) -> Dict[str, Any]:
    headers: Dict[str, Any] = {}

    _kwargs: Dict[str, Any] = {
        "method": "post",
        "url": "/query",
    }

    _body = body.to_dict()

    _kwargs["json"] = _body
    headers["Content-Type"] = "application/json"

    _kwargs["headers"] = headers
    return _kwargs


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List[Union["ApiEndpointsQueryResponse200ItemType0", "ApiEndpointsQueryResponse200ItemType1"]]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:

            def _parse_response_200_item(
                data: object,
            ) -> Union["ApiEndpointsQueryResponse200ItemType0", "ApiEndpointsQueryResponse200ItemType1"]:
                try:
                    if not isinstance(data, dict):
                        raise TypeError()
                    response_200_item_type_0 = ApiEndpointsQueryResponse200ItemType0.from_dict(data)

                    return response_200_item_type_0
                except:  # noqa: E722
                    pass
                if not isinstance(data, dict):
                    raise TypeError()
                response_200_item_type_1 = ApiEndpointsQueryResponse200ItemType1.from_dict(data)

                return response_200_item_type_1

            response_200_item = _parse_response_200_item(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List[Union["ApiEndpointsQueryResponse200ItemType0", "ApiEndpointsQueryResponse200ItemType1"]]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsQueryBody,
) -> Response[List[Union["ApiEndpointsQueryResponse200ItemType0", "ApiEndpointsQueryResponse200ItemType1"]]]:
    """Query

     Returns metrics data

    Args:
        body (ApiEndpointsQueryBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List[Union['ApiEndpointsQueryResponse200ItemType0', 'ApiEndpointsQueryResponse200ItemType1']]]
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
    body: ApiEndpointsQueryBody,
) -> Optional[List[Union["ApiEndpointsQueryResponse200ItemType0", "ApiEndpointsQueryResponse200ItemType1"]]]:
    """Query

     Returns metrics data

    Args:
        body (ApiEndpointsQueryBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List[Union['ApiEndpointsQueryResponse200ItemType0', 'ApiEndpointsQueryResponse200ItemType1']]
    """

    return sync_detailed(
        client=client,
        body=body,
    ).parsed


async def asyncio_detailed(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsQueryBody,
) -> Response[List[Union["ApiEndpointsQueryResponse200ItemType0", "ApiEndpointsQueryResponse200ItemType1"]]]:
    """Query

     Returns metrics data

    Args:
        body (ApiEndpointsQueryBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List[Union['ApiEndpointsQueryResponse200ItemType0', 'ApiEndpointsQueryResponse200ItemType1']]]
    """

    kwargs = _get_kwargs(
        body=body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsQueryBody,
) -> Optional[List[Union["ApiEndpointsQueryResponse200ItemType0", "ApiEndpointsQueryResponse200ItemType1"]]]:
    """Query

     Returns metrics data

    Args:
        body (ApiEndpointsQueryBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List[Union['ApiEndpointsQueryResponse200ItemType0', 'ApiEndpointsQueryResponse200ItemType1']]
    """

    return (
        await asyncio_detailed(
            client=client,
            body=body,
        )
    ).parsed
