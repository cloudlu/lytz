/**
 *
 */
package kx;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cloudlu
 *
 */
@Getter
@Setter
public class KDBServer {

    @NotNull
    @Size(min = 2, max = 30)
    private String host;
    @NotNull
    private int port;
    @NotNull
    @Size(min = 2, max = 30)
    private String username;
    @NotNull
    @Size(min = 2, max = 30)
    private String password;
}
