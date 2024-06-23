export async function load({locals}){
  console.log(locals);
    return {
      session: await locals.getSession(),
    }
  }