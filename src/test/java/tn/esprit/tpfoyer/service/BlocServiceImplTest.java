package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlocServiceImplTest {
    @Mock
    BlocRepository blocRepository;

    @InjectMocks
    BlocServiceImpl blocService;

    @Test
    public void testRetrieveAllBlocs() {
        // Given
        List<Bloc> blocs = Arrays.asList(
                new Bloc(1L, "Bloc1", 10, null, new HashSet<>()),
                new Bloc(2L, "Bloc2", 20, null, new HashSet<>())
        );
        when(blocRepository.findAll()).thenReturn(blocs);

        // When
        List<Bloc> result = blocService.retrieveAllBlocs();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(blocRepository).findAll(); // Suppression de times(1)
    }

    @Test
    public void testRetrieveBlocsSelonCapacite() {
        // Given
        List<Bloc> blocs = Arrays.asList(
                new Bloc(1L, "Bloc1", 10, null, new HashSet<>()),
                new Bloc(2L, "Bloc2", 20, null, new HashSet<>())
        );
        when(blocRepository.findAll()).thenReturn(blocs);

        // When
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(15);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bloc2", result.get(0).getNomBloc());
    }

    @Test
    public void testRetrieveBloc() {
        // Given
        Bloc bloc = new Bloc(1L, "Bloc1", 10, null, new HashSet<>());
        when(blocRepository.findById(anyLong())).thenReturn(Optional.of(bloc));

        // When
        Bloc result = blocService.retrieveBloc(1L);

        // Then
        assertNotNull(result);
        assertEquals("Bloc1", result.getNomBloc());
    }

    @Test
    public void testAddBloc() {
        // Given
        Bloc bloc = new Bloc(1L, "Bloc1", 10, null, new HashSet<>());
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // When
        Bloc result = blocService.addBloc(bloc);

        // Then
        assertNotNull(result);
        assertEquals("Bloc1", result.getNomBloc());
        verify(blocRepository).save(bloc); // Suppression de times(1)
    }

    @Test
    public void testModifyBloc() {
        // Given
        Bloc bloc = new Bloc(1L, "Bloc1", 10, null, new HashSet<>());
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // When
        Bloc result = blocService.modifyBloc(bloc);

        // Then
        assertNotNull(result);
        assertEquals("Bloc1", result.getNomBloc());
        verify(blocRepository).save(bloc); // Suppression de times(1)
    }

    @Test
    public void testRemoveBloc() {
        // When
        blocService.removeBloc(1L);

        // Then
        verify(blocRepository).deleteById(1L); // Suppression de times(1)
    }

    @Test
    public void testTrouverBlocsSansFoyer() {
        // Given
        List<Bloc> blocs = Arrays.asList(
                new Bloc(1L, "Bloc1", 10, null, new HashSet<>())
        );
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(blocs);

        // When
        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bloc1", result.get(0).getNomBloc());
    }

    @Test
    public void testTrouverBlocsParNomEtCap() {
        // Given
        List<Bloc> blocs = Arrays.asList(
                new Bloc(1L, "Bloc1", 10, null, new HashSet<>())
        );
        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc1", 10)).thenReturn(blocs);

        // When
        List<Bloc> result = blocService.trouverBlocsParNomEtCap("Bloc1", 10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bloc1", result.get(0).getNomBloc());
    }
}
