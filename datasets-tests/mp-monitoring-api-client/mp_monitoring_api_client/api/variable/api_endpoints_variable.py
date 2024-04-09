from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.api_endpoints_variable_body import ApiEndpointsVariableBody
from ...models.api_endpoints_variable_response_200_type_0_item import ApiEndpointsVariableResponse200Type0Item
from ...models.dataframe import Dataframe
from ...types import Response


def _get_kwargs(
    *,
    body: ApiEndpointsVariableBody,
) -> Dict[str, Any]:
    headers: Dict[str, Any] = {}

    _kwargs: Dict[str, Any] = {
        "method": "post",
        "url": "/variable",
    }

    _body = body.to_dict()

    _kwargs["json"] = _body
    headers["Content-Type"] = "application/json"

    _kwargs["headers"] = headers
    return _kwargs


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[Union["Dataframe", List["ApiEndpointsVariableResponse200Type0Item"]]]:
    if response.status_code == HTTPStatus.OK:

        def _parse_response_200(data: object) -> Union["Dataframe", List["ApiEndpointsVariableResponse200Type0Item"]]:
            try:
                if not isinstance(data, list):
                    raise TypeError()
                response_200_type_0 = []
                _response_200_type_0 = data
                for response_200_type_0_item_data in _response_200_type_0:
                    response_200_type_0_item = ApiEndpointsVariableResponse200Type0Item.from_dict(
                        response_200_type_0_item_data
                    )

                    response_200_type_0.append(response_200_type_0_item)

                return response_200_type_0
            except:  # noqa: E722
                pass
            if not isinstance(data, dict):
                raise TypeError()
            response_200_type_1 = Dataframe.from_dict(data)

            return response_200_type_1

        response_200 = _parse_response_200(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[Union["Dataframe", List["ApiEndpointsVariableResponse200Type0Item"]]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsVariableBody,
) -> Response[Union["Dataframe", List["ApiEndpointsVariableResponse200Type0Item"]]]:
    """Variable

     Returns data for Variable of type `Query`

    Args:
        body (ApiEndpointsVariableBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Union['Dataframe', List['ApiEndpointsVariableResponse200Type0Item']]]
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
    body: ApiEndpointsVariableBody,
) -> Optional[Union["Dataframe", List["ApiEndpointsVariableResponse200Type0Item"]]]:
    """Variable

     Returns data for Variable of type `Query`

    Args:
        body (ApiEndpointsVariableBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Union['Dataframe', List['ApiEndpointsVariableResponse200Type0Item']]
    """

    return sync_detailed(
        client=client,
        body=body,
    ).parsed


async def asyncio_detailed(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsVariableBody,
) -> Response[Union["Dataframe", List["ApiEndpointsVariableResponse200Type0Item"]]]:
    """Variable

     Returns data for Variable of type `Query`

    Args:
        body (ApiEndpointsVariableBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Union['Dataframe', List['ApiEndpointsVariableResponse200Type0Item']]]
    """

    kwargs = _get_kwargs(
        body=body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: Union[AuthenticatedClient, Client],
    body: ApiEndpointsVariableBody,
) -> Optional[Union["Dataframe", List["ApiEndpointsVariableResponse200Type0Item"]]]:
    """Variable

     Returns data for Variable of type `Query`

    Args:
        body (ApiEndpointsVariableBody):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Union['Dataframe', List['ApiEndpointsVariableResponse200Type0Item']]
    """

    return (
        await asyncio_detailed(
            client=client,
            body=body,
        )
    ).parsed
