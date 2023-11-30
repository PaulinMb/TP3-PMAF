using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WinFormapp
{
    internal class ApiBingMaps
    {
        private HttpClient _httpClient;

        private string _apiKey = "Amnyy0AaG5JBL3q0-mCI5h6NV87PBqlxWtBtH9r70X6CPxKOA4fZucOLgtZnyvOx";

        private string url = "https://dev.virtualearth.net/REST/V1/Imagery/Copyright/fr-CA/RoadOnDemand/16/46.643840892634046/-71.24691188285227/46.654655329056794";

        public ApiBingMaps(HttpClient httpClient)
        {
            _httpClient = new HttpClient();
        }

        public async Task callApi()
        {
            this._httpClient.
        }
    }
}
