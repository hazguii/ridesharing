export async function findRSOfferPricing(token,rsOfferId) {
  return fetch(`http://127.0.0.1:8081/price/`+rsOfferId,{
    method:'GET',
    headers:{
    'Content-Type':'application/json',
    Authorization: 'Bearer '+token
    }
  }).then(data=>data.json())
  .catch(error=>console.log(error)); 
}