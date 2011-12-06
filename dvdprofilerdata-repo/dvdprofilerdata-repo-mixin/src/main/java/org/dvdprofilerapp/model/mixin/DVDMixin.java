package org.dvdprofilerapp.model.mixin;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * DVD mixin to pervent jackson dependencies in model project
 * 
 * @author Chris
 * 
 */
// @JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include = Inclusion.NON_NULL)
public abstract class DVDMixin {

}
