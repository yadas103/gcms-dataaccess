package com.pfizer.gcms.dataaccess.repository;


import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.model.CredentialModel;

public class CredentialRepository extends AbstractRepository<CredentialModel> {
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

	public CredentialRepository(Locale country) {
		setModelType(CredentialModel.class);
	}
	
}
