package com.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningController {

	//URI Versioning
	@GetMapping("/v1/person")
	public PersonV1 personV1() {
		return new PersonV1("Ahmed Raza");
	}

	@GetMapping("/v2/person")
	public PersonV2 personV2() {
		return new PersonV2(new Name("Ahmed", "Raza"));
	}

	//Request Parameter Versioning
	@GetMapping(value="/person/param", params="version=1")
	public PersonV1 personV1Param() {
		return new PersonV1("Ahmed Raza");
	}

	@GetMapping(value="/person/param", params="version=2")
	public PersonV2 personV2Param() {
		return new PersonV2(new Name("Ahmed", "Raza"));
	}

	//Header Versioning
	@GetMapping(value="/person/header", headers="X-API-VERSION=1")
	public PersonV1 personV1Header() {
		return new PersonV1("Ahmed Raza");
	}

	@GetMapping(value="/person/header", headers="X-API-VERSION=2")
	public PersonV2 personV2Header() {
		return new PersonV2(new Name("Ahmed", "Raza"));
	}

	//Media-Type Versioning
	@GetMapping(value="/person/produces", produces="application/abc.xyz.app-v1+json")
	public PersonV1 personV1Produces() {
		return new PersonV1("Ahmed Raza");
	}

	@GetMapping(value="/person/produces", produces="application/abc.xyz.app-v2+json")
	public PersonV2 personV2Produces() {
		return new PersonV2(new Name("Ahmed", "Raza"));
	}
}
