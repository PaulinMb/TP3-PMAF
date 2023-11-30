import { useState } from 'react';
import logo from './logo.svg';
import './App.css';


function App() {
  const [route,setRoute] = useState(""); 
  const url = "http://localhost:8080/getBestRoute"

  let requestOptions={
    method : 'Get'
};
  const getRouteApi = ()=>{
      fetch(url,requestOptions).then(response=>{
       return response.text()
      }).then(data=>{ 
        console.log(data)
        setRoute(data)
      }).catch(err=>{
        console.log(err)})
  }


  return (
    <div className="App">
      <header className="App-header">
        <h2></h2>
        <div className='maindiv'>
          <h2>Livreur React</h2>
          <label>Route : </label><span >{route}</span>
          <br></br>
          <button onClick={getRouteApi}>Get route</button>
        </div> 
      </header>
    </div>
  );
}

export default App;
