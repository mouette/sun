from fastapi import FastAPI, Response
from fastapi.staticfiles import StaticFiles
import uvicorn

import requests
import calendar

from pprint import pprint

# %%
base = "https://re.jrc.ec.europa.eu/api/v5_2/PVcalc"
params = {'angle': '35',
          'aspect': '0',
          'browser': '1',
          'js': '1',
          'lat': '45.830',
          'lon': '4.881',
          'loss': '14',
          'mountingplace': 'free',
          'outputformat': 'json',
          'peakpower': '1',
          'pvtechchoice': 'crystSi',
          'raddatabase': 'PVGIS-SARAH2',
          'select_database_grid': 'PVGIS-SARAH2',
          'usehorizon': '1',
          'userhorizon': ''}

app = FastAPI()


@app.get("/api/measures")
async def  getpos(position: str, response: Response):
    lat, lon = position.split(",")
    print(lat,lon)
    params["lat"] = lat
    params["lon"] = lon
    r = requests.get(base, params=params)
    result = r.json()
    months = result["outputs"]["monthly"]["fixed"]
    e = [{"month":month["month"],"e":month["E_m"]} for month in months]
    response.headers["HX-Trigger"] = "new-measure"
    return e

@app.get("/proxy")
async def  getpos(position: str, response: Response):
    r = requests.get("http://localhost:8080/api/measures", params={"position":position})
    result = r.json()
    return result

app.mount("/", StaticFiles(directory="../front", html=True), name="static")

uvicorn.run(app, port=8000)