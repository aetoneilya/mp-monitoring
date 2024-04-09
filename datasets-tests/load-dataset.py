import os
import sys

from mp_monitoring_api_client.models.metric_dto import MetricDto


lib_path = os.path.realpath('mp-monitoring-api-client')  # Specify the absolute path to '/lib'
sys.path.append(lib_path)


import pandas as pd
from mp_monitoring_api_client import Client
from mp_monitoring_api_client.api.metrics import store_metrics
from mp_monitoring_api_client.api.project import create_project
from mp_monitoring_api_client.models import store_metrics_request
from mp_monitoring_api_client.models.project_dto import ProjectDto

client = Client(base_url="localhost:8082") # or maybe don't

with client as client:
    projectDto = ProjectDto(name="ny-taxi-test")
    my_data: ProjectDto = create_project.sync(client=client, body=projectDto)


taxi_df = pd.read_csv('datasets/taxi/dataset.csv')
taxi_df.drop('Unnamed: 0', axis=1, inplace=True)
print(taxi_df.head())

metrics=[]

for dp in taxi_df:
    dp['time']
    metrics.append(MetricDto)

request = store_metrics_request.StoreMetricsRequest(metrics=metrics)

with client as client:
    store_metrics.sync("project", request)