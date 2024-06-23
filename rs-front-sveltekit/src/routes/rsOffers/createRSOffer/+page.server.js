import {createRSOffer} from "$lib/api/rs-offers-management-client.js";
import { fail, redirect } from '@sveltejs/kit';
export const actions = {
    default: async ({locals,request}) => {
        const session = await locals.getSession();
        const token = session.accessToken;

        const data = await request.formData();

        let createRSOfferDTO = {
            "departureAddress": {
              "city": "string",
              "street": "string",
              "number": 0,
              "location": "string"
            },
            "departureGeoPoint":{
              "longitude":0,
              "latitude":0
            },
            "destinationAddress": {
              "city": "string",
              "street": "string",
              "number": 0,
              "location": "string"
            },
            "destinationGeoPoint":{
              "longitude":0,
              "latitude":0
            },
            "availableSeatsNumber": {
              "value": 0
            },
            "departureDateTime": ""
          };
        let departureDate = data.get('departureDate')+'';
        let s = departureDate.split('/') ;
        departureDate=s[2]+'-'+s[1]+'-'+s[0];
        let departureTime = data.get('departureTime');
        let departureTimeSplit = departureTime.split(':');
        let hrs = parseInt(departureTimeSplit[0]);
        let mnts = parseInt(departureTimeSplit[1]);
        let ampm = data.get('departureTimeAMPM');
        if (ampm == "pm" && hrs < 12) hrs = hrs + 12;
        if (ampm == "am" && hrs == 12) hrs = hrs - 12;
        let hours = hrs.toString();
        let minutes = mnts.toString();
        if (hrs < 10) hours = "0" + hours;
        if (mnts < 10) minutes = "0" + minutes;
        departureTime = hours + ":" + minutes;
        createRSOfferDTO.departureDateTime = departureDate+"T"+departureTime;
        createRSOfferDTO.departureAddress.city=data.get('departureAddress');
        createRSOfferDTO.departureGeoPoint.longitude=data.get('departureGeoPointLongitude');
        createRSOfferDTO.departureGeoPoint.latitude=data.get('departureGeoPointLatitude');
        createRSOfferDTO.destinationAddress.city=data.get('destinationAddress');
        createRSOfferDTO.destinationGeoPoint.longitude=data.get('destinationGeoPointLongitude');
        createRSOfferDTO.destinationGeoPoint.latitude=data.get('destinationGeoPointLatitude');
        createRSOfferDTO.availableSeatsNumber.value=data.get('availableSeatsNumber');

        let newOfferId = await createRSOffer(token,createRSOfferDTO);
        if(newOfferId){
          throw redirect(303, '/rsOffers/'+newOfferId.value);
        }else{
          return fail(400,{error:true,message:'could not create the offer'});
        }        
    },
  };
