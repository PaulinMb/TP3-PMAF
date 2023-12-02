using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WinFormapp
{
    internal class ApiCall
    {
        private HttpClient _httpClient;


        private string url = "http://172.20.45.38:8080/ServiceREST.PMAF-0.0.1-SNAPSHOT/getBestRoute";

        public ApiCall()
        {
            _httpClient = new HttpClient();
        }

        public async Task<string> callApi()
        {
            string route = "";

            try
            {

               HttpResponseMessage response =  await _httpClient.GetAsync(url);

                if (response.IsSuccessStatusCode)
                {
                    route = await response.Content.ReadAsStringAsync();
                    Debug.WriteLine(route);
                }
                else
                {
                    route = "ERROR = Erreur d'appel api rest :"+ response.StatusCode;
                    Debug.WriteLine(route);
                }

            }catch (Exception ex)
            {
                route = "ERROR = Exception survenue lors de l'appel d'api";
                Debug.WriteLine(route);
            }

            return route;
        }
    }
}
