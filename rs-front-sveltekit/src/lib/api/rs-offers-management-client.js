export async function findAllRSOffersSansSecturite() {
    return fetch(`http://localhost:8080/rsOffers`)
    .then(data=>data.json()) 
  .catch(error=>console.log(error)); 
  
}

export async function findRSOfferById(token,id) {
  return fetch(`http://127.0.0.1:8080/rsOffers/`+id,{
    method:'GET',
    headers:{
    'Content-Type':'application/json',
    Authorization: 'Bearer '+token
    }
  }).then(data=>data.json())
  .catch(error=>console.log(error)); 
}

export async function findAllRSOffers(token) {
  return fetch(`http://127.0.0.1:8080/rsOffers`,{
    method:'GET',
    headers:{
    'Content-Type':'application/json',
    Authorization: 'Bearer '+token
    }
  }).then(data=>data.json())
  .catch(error=>console.log(error)); 
}

export async function createRSOffer(token,createRSOfferDTO) {
  return fetch(`http://127.0.0.1:8080/rsOffers`,{
    method:'POST',
    headers:{
      'Content-Type':'application/json',
      Authorization: 'Bearer '+token
    },
    body: JSON.stringify(createRSOfferDTO)
  }).then(data=>data.json())
  .catch(error=>console.log(error)); 
}

export async function publishRSOffer(token,rsOfferId) {
  const publishRSOfferDTO={
    "rsOfferId": {
      "value": rsOfferId
    }
  };
  return fetch(`http://127.0.0.1:8080/rsOffers/publish`,{
    method:'POST',
    headers:{
      'Content-Type':'application/json',
      Authorization: 'Bearer '+token
    },
    body: JSON.stringify(publishRSOfferDTO)
  })
  .catch(error=>console.log(error));   
}

export async function cancelRSOffer(token,rsOfferId) {
  const cancelRSOfferDTO={
    "rsOfferId": {
      "value": rsOfferId
    }
  };
  return fetch(`http://127.0.0.1:8080/rsOffers/cancel`,{
    method:'POST',
    headers:{
      'Content-Type':'application/json',
      Authorization: 'Bearer '+token
    },
    body: JSON.stringify(cancelRSOfferDTO)
  })
  .catch(error=>console.log(error)); 
}