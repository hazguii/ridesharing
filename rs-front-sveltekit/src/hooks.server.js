import { KEYCLOAK_ID, KEYCLOAK_SECRET, KEYCLOAK_ISSUER, AUTH_SECRET } from "$env/static/private"
import { SvelteKitAuth } from "@auth/sveltekit"
import Keycloak from "@auth/core/providers/keycloak"

export const handle = SvelteKitAuth({
  providers: [Keycloak({ 
    clientId: KEYCLOAK_ID, 
    clientSecret: KEYCLOAK_SECRET,
    issuer: KEYCLOAK_ISSUER,
    profile(profile){
      return { id:profile.sub, role: profile.role ?? "anonymous", ...profile }
    }
  })],
  // useSecureCookies: false,
	// trustHost: true,
	//debug:true,
  callbacks: {
    async jwt({ token, account, user }) {
      // Persist the OAuth access_token to the token right after signin or update of the token
      // console.log('- callback jwt token : '+JSON.stringify(token))
      //console.log('- callback jwt user : '+JSON.stringify(user))
      //console.log('- callback jwt   account : '+JSON.stringify(account))
      if (account) {
        token.accessToken = account.access_token
      }
      if(user){
        token.roles = user.groups
        token.userId = user.id
      } 
      return token
    },
    async session({ session, token }) {
      session.accessToken = token.accessToken
      session.user.roles = token.roles
      session.user.id = token.userId
      //console.info(session);
      return session
    }
  }
});