package gorrita.com.wifipos.db;

import java.io.Serializable;

/**
 * Created by salva on 21/08/15.
 */
public abstract class ComunDB implements Serializable {


    private Integer id;
    private CharSequence description;
    private Long dataCreated;
    private Long dataUpdateted;
    private Integer active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CharSequence getDescription() {
        return description;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    public Long getDataCreated() {
        return dataCreated;
    }

    public void setDataCreated(Long dataCreated) {
        this.dataCreated = dataCreated == null ? System.currentTimeMillis():dataCreated;
    }

    public Long getDataUpdateted() {
        return dataUpdateted;
    }

    public void setDataUpdateted(Long dataUpdateted) {
        this.dataUpdateted = dataUpdateted == null ? System.currentTimeMillis():dataUpdateted;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active == null ? 1:active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComunDB)) return false;

        ComunDB comunDB = (ComunDB) o;

        return getId().equals(comunDB.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
