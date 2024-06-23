import {findRSOfferById,cancelRSOffer,publishRSOffer} from "$lib/api/rs-offers-management-client.js";
import {findRSOfferPricing} from "$lib/api/rs-offers-pricing-client.js";

import { fail, redirect } from '@sveltejs/kit';

export async function load({params,locals}) {
  const session = await locals.getSession();
  const token = session.accessToken;

  return {
        rsOffer : findRSOfferById(token,params.rsOfferId),
        rsOfferPricing: findRSOfferPricing(token,params.rsOfferId)
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
        throw redirect(303, '/rsOffers/'+rsOfferId);
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
        throw redirect(303, '/rsOffers/'+rsOfferId);
      }else{
        return fail(400,{error:true,message:'could not publish the offer, try later'});
      }  
    }
  };


