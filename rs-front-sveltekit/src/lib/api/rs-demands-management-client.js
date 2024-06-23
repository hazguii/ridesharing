export async function findAllRSDemandsSansSecturite() {
  return fetch(`http://localhost:8080/rsDemands`)
    .then(data=>data.json()) 
  .catch(error=>console.log(error)); }

export async function findRSDemandById(token,id) {
  return fetch(`http://127.0.0.1:8090/rsDemands/`+id,{
    method:'GET',
    headers:{
    'Content-Type':'application/json',
    Authorization: 'Bearer '+token
    }
  }).then(data=>data.json())
  .catch(error=>console.log(error));     
}

export async function findAllRSDemands(token) {
  return fetch(`http://127.0.0.1:8090/rsDemands`,{
    method:'GET',
    headers:{
    'Content-Type':'application/json',
    Authorization: 'Bearer '+token
    }
  }).then(data=>data.json())
  .catch(error=>console.log(error));    
}

export async function createRSDemand(token,createRSDemandDTO) {
  return fetch(`http://127.0.0.1:8090/rsDemands`,{
    method:'POST',
    headers:{
      'Content-Type':'application/json',
      Authorization: 'Bearer '+token
    },
    body: JSON.stringify(createRSDemandDTO)
  }).then(data=>data.json())
  .catch(error=>console.log(error));   
}

export async function publishRSDemand(token,rsDemandId) {
  const publishRSDemandDTO={
    "rsDemandId": {
      "value": rsDemandId
    }
  };
  return fetch(`http://127.0.0.1:8090/rsDemands/publish`,{
    method:'POST',
    headers:{
      'Content-Type':'application/json',
      Authorization: 'Bearer '+token
    },
    body: JSON.stringify(publishRSDemandDTO)
  })
  .catch(error=>console.log(error)); 
}

export async function cancelRSDemand(token,rsDemandId) {
  const cancelRSDemandDTO={
    "rsDemandId": {
      "value": rsDemandId
    }
  };
  return fetch(`http://127.0.0.1:8090/rsDemands/cancel`,{
    method:'POST',
    headers:{
      'Content-Type':'application/json',
      Authorization: 'Bearer '+token
    },
    body: JSON.stringify(cancelRSDemandDTO)
  })
  .catch(error=>console.log(error)); 
}