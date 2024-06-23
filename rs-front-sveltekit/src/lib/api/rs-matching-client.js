export async function findRSDemandMatchRequest(token,id) {
  return fetch(`http://127.0.0.1:8091/rsDemandMatchRequests/`+id,{
    method:'GET',
    headers:{
    'Content-Type':'application/json',
    Authorization: 'Bearer '+token
    }
  }).then(data=>data.json())
  .catch(error=>console.log(error));     
}

export async function findRSDemandProposals(token,id) {
  return fetch(`http://127.0.0.1:8091/rsProposal/findByDemandId/`+id,{
    method:'GET',
    headers:{
    'Content-Type':'application/json',
    Authorization: 'Bearer '+token
    }
  }).then(data=>data.json())
  .catch(error=>console.log(error));     
}

