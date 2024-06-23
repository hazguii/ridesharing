import {createRSDemand} from "$lib/api/rs-demands-management-client.js";
import { fail, redirect } from '@sveltejs/kit';
export const actions = {
    default: async ({locals,request}) => {
        const session = await locals.getSession();
        const token = session.accessToken;

        const data = await request.formData();

        let createRSDemandDTO = {
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
        createRSDemandDTO.departureDateTime = departureDate+"T"+departureTime;
        createRSDemandDTO.departureAddress.city=data.get('departureAddress');
        createRSDemandDTO.departureGeoPoint.longitude=data.get('departureGeoPointLongitude');
        createRSDemandDTO.departureGeoPoint.latitude=data.get('departureGeoPointLatitude');
        createRSDemandDTO.destinationAddress.city=data.get('destinationAddress');
        createRSDemandDTO.destinationGeoPoint.longitude=data.get('destinationGeoPointLongitude');
        createRSDemandDTO.destinationGeoPoint.latitude=data.get('destinationGeoPointLatitude');

        let newDemandId = await createRSDemand(token,createRSDemandDTO);
        if(newDemandId){
          throw redirect(303, '/rsDemands/'+newDemandId.value);
        }else{
          return fail(400,{error:true,message:'could not create the demand'});
        }
    },
  };