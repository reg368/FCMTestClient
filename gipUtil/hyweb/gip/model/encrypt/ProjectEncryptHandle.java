package hyweb.gip.model.encrypt;

import hyweb.gip.pojo.mybatis.table.InfoUser;

/**
 * 與專案有關的加解密
 * @author Istar
 *
 */
public interface ProjectEncryptHandle extends EncryptHandle {
	public InfoUser encryptInfoUser(InfoUser infoUser, String userid);

	public InfoUser decryptInfoUser(InfoUser infoUser);
}
