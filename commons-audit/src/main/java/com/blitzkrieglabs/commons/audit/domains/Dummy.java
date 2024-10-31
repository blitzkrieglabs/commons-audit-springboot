package com.blitzkrieglabs.commons.audit.domains;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/* Springboot is causing error that Stateful is not a managed entity, when someone is not inheritting it. 
	so we need Dummy to inherit to fix Spring loader
*/
@Entity
@SuperBuilder
public class Dummy extends Stateful{ 

}
