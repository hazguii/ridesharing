import {findAllRSOffers,cancelRSOffer,publishRSOffer} from "$lib/api/rs-offers-management-client.js";
import { fail, redirect } from '@sveltejs/kit';
import { findAllRSOffersSansSecturite } from "../../../lib/api/rs-offers-management-client";

export async function load({locals}) {
  const session = await locals.getSession();
  const token = session.accessToken;
  return {
    rsOffers : findAllRSOffersSansSecturite()
    
  };
}


export const actions = {
  cancel: async ({locals,request}) => {
    const data = await request.formData();
    const rsOfferId = data.get('rsOfferId');
    //console.log('cancelling rsOfferID '+rsOfferId);
    const session = await locals.getSession();
    const token = session.accessToken;
    
    const res = await cancelRSOffer(token,rsOfferId);
    if(res){
      throw redirect(303, '/rsOffers/list');
    }else{
      return fail(400,{error:true,message:'could not cancel the offer, try later'});
    }
  },
  publish: async ({locals,request}) => {
    const data = await request.formData();
    const rsOfferId = data.get('rsOfferId');
    //console.log('publishing rsOfferID '+rsOfferId);
    const session = await locals.getSession();
    const token = session.accessToken;
    
    const res = await publishRSOffer(token,rsOfferId);
    if(res){
      throw redirect(303, '/rsOffers/list');
    }else{
      return fail(400,{error:true,message:'could not publish the offer, try later'});
    }  
  }
};


