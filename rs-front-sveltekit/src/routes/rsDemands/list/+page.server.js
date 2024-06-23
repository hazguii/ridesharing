import {findAllRSDemands,cancelRSDemand,publishRSDemand} from "$lib/api/rs-demands-management-client.js";
import { fail, redirect } from '@sveltejs/kit';

export async function load({locals}) {
  const session = await locals.getSession();
  const token = session.accessToken;
  return {
    rsDemands : findAllRSDemands(token)
  };
}

export const actions = {
  cancel: async ({locals,request}) => {
    const data = await request.formData();
    const rsDemandId = data.get('rsDemandId');
    //console.log('cancelling rsDemandID '+rsDemandId);
    const session = await locals.getSession();
    const token = session.accessToken;
    
    const res =await cancelRSDemand(token,rsDemandId);
    if(res){
      throw redirect(303, '/rsDemands/list');
    }else{
      return fail(400,{error:true,message:'could not cancel the demand, try later'});
    }  
  },
  publish: async ({locals,request}) => {
    const data = await request.formData();
    const rsDemandId = data.get('rsDemandId');
    //console.log('publishing rsDemandID '+rsDemandId);
    const session = await locals.getSession();
    const token = session.accessToken;
    
    const res =await publishRSDemand(token,rsDemandId);
    if(res){
      throw redirect(303, '/rsDemands/list');
    }else{
      return fail(400,{error:true,message:'could not publish the demand, try later'});
    }  
},
};