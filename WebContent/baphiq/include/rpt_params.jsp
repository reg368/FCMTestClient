<%!
	private void init_params(JOWebObject web) throws Exception {
		JOFunctional.exec("web.ejo2params", web);
		JOFunctional.exec("baphiq.VGIPGrant", web);
		JSONObject params = web.params();
		params.put("numPage", params.optInt("numPage", 50));
        params.put("dv", params.opt("cityId"));
        String ht_pcity = (String) JOFunctional.exec2("ht_select", web, "pcity", params);
        web.request().setAttribute("ht_pcity", ht_pcity);
	}

	private boolean check_grant(JOWebObject web) throws Exception {
		return true;
	}
%>