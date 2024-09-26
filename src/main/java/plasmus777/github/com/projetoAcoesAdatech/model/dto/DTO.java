package plasmus777.github.com.projetoAcoesAdatech.model.dto;

//T1 (DTO) - T2 (Entity)
public interface DTO <T1, T2>{
    public T2 toEntity();
    public T1 fromEntity(T2 t2);
}
